import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class NepomucenoKarlMidtermReq {

    private static final String FILE_NAME = "input.txt"; // Constant for file name
    private static final MathContext MATH_CONTEXT = new MathContext(30); // Constant for MathContext precision
    private static final BigDecimal SMALL_VALUE = new BigDecimal("1E-20"); // Constant for small value threshold

    public static void main(String[] args) {
        File file = new File(FILE_NAME);
        
        // Check if the file exists
        if (!file.exists()) {
            System.err.println("File does not exist: " + file.getAbsolutePath()); // Print error message to standard error
            return;
        }
        
        // Try to read the text file using try-with-resources for automatic resource management
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // Try-with-resources to ensure resource is closed
            String line;

            // Check if the reader is ready to read
            if (!reader.ready()) {
                System.out.println("No data to read in the file."); // Print warning message to standard output
            }
            
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                try {
                    // Parse the line into a BigDecimal
                    BigDecimal x = new BigDecimal(line.trim()); // Trim whitespace and parse to BigDecimal
                    
                    // Calculate the hyperbolic sine of x
                    BigDecimal sinhX = sinh(x, MATH_CONTEXT); // Use constant MathContext
                    
                    // Print the result
                    System.out.println("sinh(" + x + ") = " + sinhX); // Print result in a readable format
                } catch (NumberFormatException nfe) {
                    // Handle invalid number format in the line
                    System.err.println("Invalid number format in line: " + line); // Print error message to standard error
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FILE_NAME); // Print error message to standard error
        } catch (IOException e) {
            System.err.println("Error reading from file."); // Print error message to standard error
            e.printStackTrace(); // Print stack trace for debugging purposes
        }
    }

    // Calculate sinh(x) using Taylor series expansion
    public static BigDecimal sinh(BigDecimal x, MathContext mc) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = x;
        BigDecimal xSquared = x.multiply(x, mc); // Cache x^2 for efficiency
        BigDecimal factorial = BigDecimal.ONE;
        BigDecimal num = x;
        int i = 1;

        // Loop until the term is smaller than the small value threshold
        while (term.abs(mc).compareTo(SMALL_VALUE) > 0) { // Use constant small value threshold
            result = result.add(term, mc);
            num = num.multiply(xSquared, mc); // x^(2*i + 1) term computation
            factorial = factorial.multiply(BigDecimal.valueOf(2 * i))
                                 .multiply(BigDecimal.valueOf(2 * i + 1)); // Factorial computation
            term = num.divide(factorial, mc); // Division for next term in series
            i++; // Increment iteration count
        }

        return result;
    }
}
