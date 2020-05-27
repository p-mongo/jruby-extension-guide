require 'hello-world'

describe Arg do
  let(:instance) do
    described_class.new('Hi')
  end

  it 'constructs an object with arguments' do
    expect(instance.hello_world).to eq('Hi world')
  end
end
