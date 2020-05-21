require 'java'
require 'hello-world.jar'

describe com.example.hello.HelloWorld do
  let(:instance) do
    described_class.new
  end

  it 'invokes a method using its Java name' do
    expect(instance.helloWorld).to eq('Hello, world!')
  end

  it 'invokes a method using its Ruby name' do
    expect(instance.ruby_hello_world).to eq('Ruby hello, world!')
  end

  it 'invokes a method with Ruby annotation using its Java name' do
    expect(instance.rubyHelloWorld).to eq('Ruby hello, world!')
  end
end
