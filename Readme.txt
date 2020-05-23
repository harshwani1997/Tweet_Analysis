Steps to run the program on prince:
1: First install vpn and connect to it to bypass the gateway
2: Complete the maven project in eclipse and export it as a jar file into the local system.
3: Put all the csv file, the eclipse project file and the necessary dependency files into prince using - scp hsw268@prince.hpc.nyu.edu:/scratch/hsw268/ sentiment140.csv
4: Ask for the cpu processing power -  srun --mem=5GB --time=00:15:00 --cpus-per-task 1 --pty $SHELL
5: Via cmd windows, now using the scp put that jar file into the prince cluster using the command - scp (name).jar hsw268@prince.hpc.nyu.edu:/scratch/hsw268/
6: Finally run the jar file using - java -jar TweetAnalysis.jar