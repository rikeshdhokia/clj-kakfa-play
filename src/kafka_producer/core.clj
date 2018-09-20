(ns kafka-producer.core
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

(defn send-to-topic! [topic v]
  (let [record (ProducerRecord. topic (-> v nippy/freeze))]
    (.send producer record)))

(send-to-topic! "test" {:avx 24})

(comment
  "Wait for response from send
  capture timestamp or event id
  retry on fail to send
  "
  )
