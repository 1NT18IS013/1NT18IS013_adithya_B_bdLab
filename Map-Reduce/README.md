# Map-reduce Programs

## Quicksetup 

1. Create a Java project
2. Create a `package.name` for the project
3. Create a class to write your map-reduce code
4. Add the following config files :
   
   Click on the project > Build path.. > Libraries > Add External JAR files
   
   Add the files `usr/share/hadoop/common/hadoop-common-3.1.4.jar` and  `usr/share/hadoop/map-reduce/hadoop-mapreduce-client-core-3.1.4.jar`
   
## To run the mapreduce programs

1. Extract the code as a JAR file
    `Note : Do not forget to point it to Main.class`
2. Fire up the Hadoop cluster 
    ```
        $cd $HADOOP_HOME/sbin/
        $./start-all.sh
    
    ```
3. Create a `input` directory under your root directory
4. Make sure the file to be given as input to the Map-reduce program is present inside `~/input/` otherwise
    
    `
        $hdfs dfs -put source_path_of_file ~/input/
    `
5. Execute this
    ```
        hadoop jar path_to_jar ~/input/input_file ~/output/
       
    ````
6. You can find the output files under `~/output/` directory
    ```
        $hdfs dfs -ls ~/output/
    
    ```
    To check the output `cat` into part-XXXXX file inside `~/output/`
    
    
