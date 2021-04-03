# Java Log Tester

This project is just a personal side project to explore how testing the logging of messages might be testable in a
`Spock` test class. Normally logging is done to either `stdout` (a.k.a. the console) where console output can be
redirected to and captured in a named file or to files, but then opening the application log files and parsing
through them in a Java or Groovy test class to validate specific log messages were written in a specific manner can
be quite involved.

This project attempts to provide an answer to the question "isn't there an easier way to test logging in a Java
application?" I'm not so vain as to say that this solution is necessarily the **best** implementation or approach for
testing logging. I'm sure there are othe ways that could potentially be more effective, but this was just one approach
I thought to take to solve the problem. If you have a better way of doing it, I'd love to hear your approach!

This side project was a great way for me to take myself deeper into the `Logback` framework and explore how to
develop customized `Appender` classes. This project was also a great way for me to explore the relationships of
`Layout`, `Encoder`, `Context`, `Logger`, and `Appender` classes and how they all collaborate to provide the
fundamental basics for being able to configure some pretty complex logging solutions.

To use any of this code in another project, the `StringAppender` class would necessarily need to be copied to and
included in your other project where you want to declare a `StringAppender` instance and assign it to any `Logger`
configurations.

---

Here are some of the references I used to help develop this project and better understand the Logback framework:

* [Logback Project](https://logback.qos.ch/) 
* [The Logback Manual](https://logback.qos.ch/manual/) - great details of the various logging components and what
they do
* [The Logback Classic API (version 1.2.3)](https://www.javadoc.io/doc/ch.qos.logback/logback-classic/1.2.3)
* [The logback Core API (version 1.2.3)](https://www.javadoc.io/doc/ch.qos.logback/logback-core/1.2.3)
