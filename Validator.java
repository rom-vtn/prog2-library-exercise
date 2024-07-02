public class Validator {

    public static String checkString(String string, String context) throws ValidatorException {
        Validator.checkObject(string, context);
        String trimmed = string.trim();
        if (trimmed.isEmpty()) {
            throw new ValidatorException(context + ": string is empty when trimmed");
        }
        if (!trimmed.equals(string)) {
            throw new ValidatorException(context + ": string is different than its trimmed version");
        }
        return string;
    }

    public static<T extends Number> T checkNumber(T number, T min, T max, String context) throws ValidatorException {
        if (number.doubleValue() < min.doubleValue()) {
            throw new ValidatorException(context + ": number below min");
        }
        if (max.doubleValue() < number.doubleValue()) {
            throw new ValidatorException(context + ": number above max");
        }
        return number;
    }

    public static<T> T checkObject(T obj, String context) throws ValidatorException {
        if (obj == null) {
            throw new ValidatorException(context + ": got null value");
        }
        return obj;
    }
}
