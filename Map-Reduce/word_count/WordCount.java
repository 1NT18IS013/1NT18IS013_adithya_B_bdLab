package my.wordcount.demo;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
	
public class WordCount {

//MAPPER CODE	
	   
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
private final static IntWritable one = new IntWritable(1);
private Text word = new Text();

public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	String line = value.toString();
	StringTokenizer tokenizer = new StringTokenizer(line);
	while (tokenizer.hasMoreTokens()) {
		word.set(tokenizer.nextToken());
		output.collect(word, one); //{Mary, 1} //{had, 1} {little, 1} {lamb,1} {little, 1} 
	}
	}
}

//REDUCER CODE	
public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException { //{little: {1,1}} 
	int sum = 0;
	while (values.hasNext()) {
	         sum += values.next().get(); // sum = 0 , sum <- sum + 1 < 1 , sum = 1+1 = > {key:little, sum=2}
       	}
	output.collect(key, new IntWritable(sum));
	}
}
	
//DRIVER CODE
public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(WordCount.class);
	conf.setJobName("wordcount");
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(IntWritable.class);
	conf.setMapperClass(Map.class);
	conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);
	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class); // hadoop jar jarname classpath inputfolder outputfolder
	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	JobClient.runJob(conf);   
}
}