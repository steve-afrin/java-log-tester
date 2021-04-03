package home.safrin.logback.appenders

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class StringAppenderTest extends Specification {

    /* Because the ROOT logger is additive by default, this Logger will automatically add log messages to the
     * ROOT logger as is normally the case for most any Logger created in any class for purposes of logging
     * messages at various levels. */
    private static final Logger LOG = LoggerFactory.getLogger(StringAppenderTest.class)

    def "test adding appender to logger"() {
        given:
        /* This gives us a reference to the ROOT Logger instance so we can get a reference to the
         * StringAppender instance from that Logger object and the below LOG messages will automatically
         * appear in the StringAppender instance because the ROOT Logger is additive for the Logger to
         * which messages are being written in this test. */
        def logbackLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger('root')
        def stringAppender = (StringAppender) logbackLogger.getAppender('string')
        def testClassname = StringAppenderTest.class.getName()

        when:
        LOG.trace('trace level log message')
        LOG.debug('debug level log message')
        LOG.info('info level log message')
        LOG.warn('warn level log message')
        LOG.error('error level log message')
        def logContent = stringAppender.getLogOutput()

        then:
        /* The expected content of the LOG output is based on the configuration of the logback-test.xml file. */
        logContent.length == 5
        logContent[0] == '[TRACE] ' + testClassname + ' [main ] - trace level log message'
        logContent[1] == '[DEBUG] ' + testClassname + ' [main ] - debug level log message'
        logContent[2] == '[INFO ] ' + testClassname + ' [main ] - info level log message'
        logContent[3] == '[WARN ] ' + testClassname + ' [main ] - warn level log message'
        logContent[4] == '[ERROR] ' + testClassname + ' [main ] - error level log message'
    }
}

