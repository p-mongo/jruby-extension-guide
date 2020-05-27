package com.example.hello;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.builtin.IRubyObject;

public class Arg extends RubyObject {
  public Arg(final Ruby runtime, final RubyClass rubyClass) {
    super(runtime, rubyClass);
  }
  
  private RubyString salutation;
  
  @JRubyMethod(name = "initialize")
  public IRubyObject initialize(RubyString salutation) {
    this.salutation = salutation;
    return null;
  }
  
  @JRubyMethod(name = "hello_world")
  public RubyString helloWorld() {
    return RubyString.newString(getRuntime(), salutation.toString() + " world");
  }
}
