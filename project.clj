(defproject kafka-producer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot kafka-producer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "kafka_producer.jar"
  :dependencies [[org.apache.kafka/kafka-clients "2.0.0"]
                 [org.clojure/clojure "1.9.0"]
                 [com.taoensso/nippy "2.14.0"]])
