package home.safrin.logback.appenders;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A customized implementation of the Logback framework's {@link OutputStreamAppender} that sets the underlying
 * {@link OutputStream} of the superclass to be a {@link ByteArrayOutputStream} instance because that type of
 * {@link OutputStream} can be easily converted to a {@link CharSequence} object. If the potential size of the
 * underlying {@link OutputStream} were a concern, we could even implement this class with an underlying
 * {@link BufferedOutputStream} to encapsulate the {@link ByteArrayOutputStream}, but for the purposes of
 * demonstration here, this is an unnecessary complexity we don't want to deal with at the moment.
 *
 * @param <E> the generic type for this {@link Appender} instance
 * @author Steve Afrin
 * @version 1.0
 */
public class StringAppender<E> extends OutputStreamAppender<E> {

  private Charset encodingCharset;
  private ByteArrayOutputStream byteArrayOutputStream;

  public StringAppender(final String encodingCharsetName) {
    super();
    this.setEncodingCharset(encodingCharsetName);
  }

  public StringAppender() {
    this(null);
  }

  public Charset getEncodingCharset() {
    return this.encodingCharset;
  }

  /**
   * The encoding character set for this {@link Appender} becomes relevant at the time we wish to convert the
   * underlying {@link ByteArrayOutputStream} to a {@link String} type. The resulting {@link String} instance
   * needs to encoded as a specific character set.
   *
   * @param encodingCharsetName a String value specifying the name of the character set to use for encoding
   *                            the String to represent the underlying {@link ByteArrayOutputStream} object;
   *                            if {@code null}, then the character set encoding will default to {@code UTF-8}
   */
  public void setEncodingCharset(final String encodingCharsetName) {
    Charset charset = null;

    try {
      charset = Charset.forName(encodingCharsetName);
    } catch (IllegalArgumentException ex) {
      /* IllegalArgumentException is thrown when the encodingCharsetName parameter value is null;
       * also IllegalArgumentException is a superclass of both java.nio.charset.IllegalCharsetNameException
       * and java.nio.charset.UnsupportedCharsetException which can also both be thrown by
       * java.nio.charset.Charset#forName(String).
       *
       * In the event any of these exceptions are thrown, just let the charset variable remain null and the
       * Charset will default to UTF-8 as specified in the below ternary operation and assignment.
       */
    }

    this.encodingCharset = charset == null ? StandardCharsets.UTF_8 : charset;
  }

  @Override
  public void start() {
    this.byteArrayOutputStream = new ByteArrayOutputStream();
    super.setOutputStream(this.byteArrayOutputStream);
    super.start();
  }

  /**
   * Get the current log content as an array of String objects where each String in the array is necessarily a
   * different line in the log and the array elements are necessarily in time sequential order of submission to the
   * logging system.
   *
   * @return a time sequentially ordered array of String objects where each String in the array is a different line
   * of the log output
   */
  public String[] getLogOutput() {
    /* The String#split(String) method call is potentially dangerous for any log messages that are meant to include
     * carriage returns and newlines, but there could also be an argument for don't do that! Make sure all log
     * messages are single line messages because a developer can get into other issues with searching logs for
     * errors and exceptions when you have multiple line log messages.
     *
     * In any case, this appender is fully customizable, so the implementer can put any kind of logic that suits
     * the use case requirements to return the underlying ByteArrayOutputStream in any format the developer see
     * fit. */
    return this.byteArrayOutputStream.toString(this.encodingCharset).split("\r\n");
  }
}

