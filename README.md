JRuby Extension Guide
=====================

Hello, World!
-------------

The `hello-world` subdirectory of this repository contains the simplest
possible JRuby extension.

This extension defines a single class, `HelloWorld`.

The source code for Java classes needs to be placed in a directory structure
that mirrors the class's namespace. In this case our class name is
`HelloWorld` and our package name is `com.example.hello`, thus the class should
be placed in `src/main/com/example/hello/HelloWorld.java`.

The simplest Java class definition looks as follows:

    package com.example.hello;

    public class HelloWorld {
      public String helloWorld() {
        return "Hello, world!";
      }
    }

The JRuby extension uses [rake-compiler](https://github.com/rake-compiler/rake-compiler)
to compile itself.

Once compiled, to use this class from a Ruby program, we might do the
following:

    require 'java'
    require 'hello-world.jar'

    hello = com.example.hello.HelloWorld.new
    hello.helloWorld
    # => "Hello, world!"


Ruby Method Names
-----------------

`hello.helloWorld` is not very Ruby-esque. We can use the `JRubyMethod`
annotation to define a Ruby name for our Java method, as follows:

    import org.jruby.anno.JRubyMethod;

    public class HelloWorld {
      @JRubyMethod(name = "hello_world")
      public String helloWorld() {
        return "Hello, world!";
      }
    }

This allows us to call the method more naturally:

    hello = com.example.hello.HelloWorld.new
    hello.hello_world
    # => "Hello, world!"

Note that the original method name (`helloWorld`) remains accessible in Ruby,
even when we defined a Ruby alias for it.
