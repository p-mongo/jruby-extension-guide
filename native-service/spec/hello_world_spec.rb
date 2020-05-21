require 'hello-world'

describe HelloWorld do
  let(:instance) do
    described_class.new
  end

  it 'exposes a method using its Ruby name' do
    expect(instance.hello_world).to eq('Ruby hello, world!')
  end

  it 'does not expose a method with Ruby annotation using its Java name' do
    expect do
      instance.helloWorld
    end.to raise_error(NoMethodError)
  end
end
