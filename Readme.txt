Data Mining assignment 1 Documentation:

1. Unzip the DataMiningProject1AprioriNew.zip
2. cd DataMiningProject1Apriori
3. The commands are different for linux and windows platforms which are mentioned below:
	Linux/Mac/Unix: In terminal run
		./gradlew run -PappArgs="['<min sup>','<k>','<input file>','<output file>']"

		./gradlew run -PappArgs="['4','3','/Users/arjunsubramanyamvaralakshmi/dm/transactionDB.txt','outpo.txt']" //provide fully qualified file path(absolute path)

	Windows: In Command prompt run
		gradlew run -PappArgs="['<min sup>','<k>','<input file>','<output file>']"

		ex: gradlew run -PappArgs="['4','3','/Users/arjunsubramanyamvaralakshmi/dm/transactionDB.txt','outpo.txt']"

4. the ouput file will be written to the current directory


	       
