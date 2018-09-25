(ns streams-consumer.core
  (:require [taoensso.nippy :as nippy])
  (:import [org.apache.kafka.streams KafkaStreams StreamsBuilder StreamsConfig]
           [org.apache.kafka.common.serialization Serdes]
           [org.apache.kafka.streams.kstream ValueMapper]
           [java.util Properties]))

(defn streams-config []
  (let [p (Properties.)]
    (.put p (StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG) (.getClass (Serdes/ByteArray)))
    (.put p (StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG) (.getClass (Serdes/String)))
    (.put p (StreamsConfig/BOOTSTRAP_SERVERS_CONFIG) "localhost:9092")
    (.put p (StreamsConfig/APPLICATION_ID_CONFIG) "group_1")
    p))


(defn ^ValueMapper value-mapper [f]
  (reify ValueMapper (apply [_ v]
                       (-> v
                           nippy/thaw
                           f
                           nippy/freeze))))

(defn some-transform [v]
  (assoc v :test 99999))

(defn topology []
  (-> (StreamsBuilder.)
      (.stream "new-in-2")
      (.mapValues (value-mapper some-transform))
      (.to "new-out-2")))

(defn streams []
  (KafkaStreams. (topology) (streams-config)))

(comment
  "Transform messages from 1 topic onto another."
  (def s (streams))
  (.start s)
  ;; add new messages to topic
  (.close s))

