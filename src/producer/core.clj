(ns producer.core
  (:require [taoensso.nippy :as nippy])
  (:import [org.apache.kafka.clients.producer KafkaProducer ProducerConfig ProducerRecord]
           [org.apache.kafka.common.serialization ByteArraySerializer StringSerializer]))

(def producer-config
  (assoc {}
    (ProducerConfig/CLIENT_ID_CONFIG) "kafka-producer"
    (ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG) (.getName (.getClass (StringSerializer.)))
    (ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG) (.getName (.getClass (ByteArraySerializer.)))
    (ProducerConfig/BOOTSTRAP_SERVERS_CONFIG) "localhost:9092"))

(def producer
  (KafkaProducer. producer-config))

(defn send! [record]
  (.send producer record))

(defn producer-record [topic k v]
  (ProducerRecord. topic
                   k
                   (nippy/freeze v)))


(comment
  "Use the below function to add to a topic"
  (-> (producer-record "some-topic" "some-key" {:avx 24})
      send!))