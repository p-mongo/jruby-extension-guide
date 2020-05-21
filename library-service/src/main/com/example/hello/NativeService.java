package com.example.hello;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.runtime.load.BasicLibraryService;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;

public class NativeService implements BasicLibraryService {

  public boolean basicLoad(final Ruby runtime) throws IOException {
    RubyClass object = runtime.getObject();
    
    RubyClass helloWorld = object.defineClassUnder("HelloWorld", object, new ObjectAllocator() {
      public IRubyObject allocate(Ruby runtime, RubyClass rubyClass) {
        return new HelloWorld(runtime, rubyClass);
      }
    });

    helloWorld.defineAnnotatedMethods(HelloWorld.class);
    return true;
  }
}
