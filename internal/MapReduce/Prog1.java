package my.studentmarks.demo;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;


public class Prog1 {

	//MAPPER CODE	
		   
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
		String myvalue = value.toString();
		String[] studtokens = myvalue.split(","); //splitting CSV
		
		if(Integer.parseInt(studtokens[2]>60) //checking if subject1 marks > 60
            output.collect(new Text(" Total no. of students with marks > 60 "), one);
		}
		
// 		output.collect(new Text(tokens[1]), new IntWritable(Integer.parseInt(tokens[2])));

		
	}

	//REDUCER CODE	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException { 
            int studcount = 0;
			while(values.hasNext()) {
				studcount += values.next().get();
			}
			
			output.collect(key, new IntWritable(studcount));
		}
	}
		
	//DRIVER CODE
	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(Prog1.class);
		conf.setJobName("Students with marks > 60 in subject 1");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class); 
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);   
	}
	}
