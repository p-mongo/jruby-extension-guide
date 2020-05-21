package com.example.hello;

import org.jruby.anno.JRubyMethod;

public class HelloWorld {
  public String helloWorld() {
    return "Hello, world!";
  }
  
  @JRubyMethod(name = "hello_world")
  public String rubyHelloWorld() {
    return "Ruby hello, world!";
  }
}
