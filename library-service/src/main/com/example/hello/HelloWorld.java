package com.example.hello;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.anno.JRubyMethod;

public class HelloWorld extends RubyObject {
  public HelloWorld(final Ruby runtime, final RubyClass rubyClass) {
    super(runtime, rubyClass);
  }

  @JRubyMethod(name = "hello_world")
  public RubyString helloWorld() {
    return RubyString.newString(getRuntime(), "Ruby hello, world!");
  }
}
