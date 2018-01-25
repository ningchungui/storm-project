package cloudy.spout;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import kafka.consumers.OrderConsumer;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class OrderBaseSpout implements IRichSpout {

	String topic = null;
	public OrderBaseSpout(String topic)
	{
		this.topic = topic ;
	}
	/**
	 * 公共基类spout
	 */
	private static final long serialVersionUID = 1L;
	Integer TaskId = null;
	SpoutOutputCollector collector = null;
	Queue<String> queue = new ConcurrentLinkedQueue<String>() ;
	ConcurrentLinkedQueue<String> queue2 = new ConcurrentLinkedQueue<String>();
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("order")) ;
	}

	@Override
	public void nextTuple() {
		if (queue.size() > 0) {
			String str = queue.poll() ;
			//进行数据过滤
//			System.out.println("TaskId:"+TaskId+";  str="+str);
			collector.emit(new Values(str)) ;
		}
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector ;
		TaskId = context.getThisTaskId() ;
		OrderConsumer consumer = new OrderConsumer(topic) ;
		consumer.start() ;
		queue = consumer.getQueue() ;
	}

	@Override
	public void ack(Object msgId) {
		
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void close() {
		
	}

	@Override
	public void deactivate() {
		
	}

	@Override
	public void fail(Object msgId) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
