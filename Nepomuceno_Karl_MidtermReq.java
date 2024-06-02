import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class Nepomuceno_Karl_MidtermReq {

    public static void main(String[] args) {
        // Path to the input file
        String fileName = "input.txt";
        File file = new File(fileName);
        
        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return;
        }
        
        // Try to read the text file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            // Check if the reader is ready to read
            if (!reader.ready()) {
                System.out.println("No data to read in the file.");
            }
            
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                try {
                    // Parse the line into a BigDecimal
                    BigDecimal x = new BigDecimal(line.trim());
                    
                    // Calculate the hyperbolic sine of x
                    BigDecimal sinhX = sinh(x, new MathContext(30));
                    
                    // Print the result
                    System.out.println("sinh(" + x + ") = " + sinhX);
                } catch (NumberFormatException nfe) {
                    // Handle invalid number format in the line
                    System.err.println("Invalid number format in line: " + line);
                }
            }
            
            // Close the reader
            reader.close();
        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            // Handle errors while reading from the file
            System.err.println("Error reading from file.");
            e.printStackTrace();
        }
    }

    // Calculate sinh(x) using Taylor series expansion
    public static BigDecimal sinh(BigDecimal x, MathContext mc) {
        // Set a small value threshold for terminating the series expansion
        final BigDecimal smallValue = new BigDecimal("1E-20");
        
        // Initialize result and the first term of the series
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = x;
        
        // Precompute x^2 to use in series terms
        BigDecimal xSquared = x.multiply(x, mc);
        
        // Initialize factorial and numerator
        BigDecimal factorial = BigDecimal.ONE;
        BigDecimal num = x;

        // Variable to keep track of the series iteration
        int i = 1;
        
        // Loop until the term is smaller than the small value threshold
        while (term.abs(mc).compareTo(smallValue) > 0) {
            // Add the current term to the result
            result = result.add(term, mc);
            
            // Compute the next term in the series
            num = num.multiply(xSquared, mc); // x^(2*i + 1)
            factorial = factorial.multiply(BigDecimal.valueOf(2 * i))
                    .multiply(BigDecimal.valueOf(2 * i + 1));
            term = num.divide(factorial, mc);
            
            // Increment the iteration count
            i++;
        }
        
        // Return the final result of the series expansion
        return result;
    }
}
