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


Library Service
---------------

There is yet another minor issue with our extension - the class name is
`com.example.hello.HelloWorld` and not `HelloWorld`. To fix this, we can use
a library service.

The example code for this extension is in the `library-service` subdirectory.

Create a file `src/main/com/example/hello/HelloWorld.java` with the
following contents:

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

This service defines one method, `basicLoad`, which creates a `HelloWorld`
class in the Ruby namespace using the `HelloWorld` Java class as its
implementation.

To invoke this service, create a file `lib/hello-world.rb` with the
following contents:

    require 'hello-world-native'

    com.example.hello.NativeService.new.basicLoad(JRuby.runtime)

We also need to adjust our `HelloWorld` Java implementation. All of the
methods that are invoked from Ruby must now return Ruby types, not Java
types. Change `src/main/com/example/hello/HelloWorld.java` to:

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

The following modifications were needed:

- `HelloWorld` class now derives from `RubyObject`.
- The class must define a constructor taking `runtime` and `rubyClass` arguments.
- `JRubyMethod` annotation is required.
- `helloWorld` method must now return `RubyString` and not Java `String`.

Now, instead of the clients of the library needing to require the `.jar` file,
a Ruby file provided with the library will do so; subsequently, this
bootstrap file will load our service which will define the `HelloWorld`
Ruby class.

To use this extension, we might do the following:

    require 'hello-world'

    hello = HelloWorld.new

    hello.hello_world
    # => "Hello, world!"

Now the Ruby code looks like any other Ruby code. The fact that HelloWorld
is implemented in Java is completely transparent.
