import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerDemo {

	public ProducerConsumerDemo() {
		List<Integer> set = new ArrayList<Integer>();
		new Producer(set).start();
		new Consumer(set).start();
	}

	public static void main(String[] args) {
		new ProducerConsumerDemo();
	}

	class Producer extends Thread {
		List<Integer> set;

		public Producer(List<Integer> set) {
			this.set = set;
		}

		@Override
		public void run() {

			while (true) {

				synchronized (set) {
					while (set.size() > 3)
						try {
							set.wait();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					int num = set.size();
					System.out.println("Add " + (++num) );

					set.add(num);
					set.notifyAll();

				}
				sleepRandomTime();
			}

		}

		private void sleepRandomTime() {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class Consumer extends Thread {
		List<Integer> set;

		public Consumer(List<Integer> set) {
			this.set = set;
		}

		@Override
		public void run() {

			while (true) {

				synchronized (set) {
					while (set.size() < 1)
						try {
							set.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					System.out.println("Remove..." + set.get(set.size() - 1));
					set.remove(set.size() - 1);
					set.notifyAll();

				}
				sleepRandomTime();
			}
		}

		private void sleepRandomTime() {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
