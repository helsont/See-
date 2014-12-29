package maze;

import java.util.concurrent.LinkedBlockingQueue;

import algorithm_base.AlgorithmState;

public class HangThread {
	private LinkedBlockingQueue<AlgorithmState> queue;

	public HangThread(LinkedBlockingQueue<AlgorithmState> queue) {
		this.queue = queue;
	}
	
	public void hang() {
		
	}
}
