FROM olzhabay/hadoop-compiled

MAINTAINER olzhabay <olzhabay.i@gmail.com>

RUN mkdir -p /root/HadoopBasedApriori/classes
COPY src /root/HadoopBasedApriori/src/.
COPY dataSet /root/HadoopBasedApriori/dataSet/.
COPY run.sh /root/HadoopBasedApriori/.
COPY upload_dataset.sh /root/HadoopBasedApriori/.

