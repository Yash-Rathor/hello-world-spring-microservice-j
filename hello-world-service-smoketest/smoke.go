package main

import (
  "fmt"
  "io/ioutil"
  "net/http"
  "os"
  "strings"
  "time"

  log "github.com/sirupsen/logrus"
)

func main() {
  log.SetFormatter(&log.JSONFormatter{})
  log.SetLevel(log.DebugLevel)
  log.Infof("Running tests")

  baseURL := os.Getenv("BASE_URL")
  if baseURL == "" {
    log.Fatalf("no BASE_URL env variable")
  }

  err := greetingTest(baseURL, 0)
  if err != nil {
    log.Fatalf("smoke tests failed with error: %v ", err)
  }
}

func greetingTest(baseURL string, iteration int) error {

  req, err := http.NewRequest("GET", fmt.Sprintf("%s/greetings/smoketest", baseURL), nil)
  if err != nil {
    return fmt.Errorf("Not able to build %s/greetings GET request", baseURL)
  }
  req.Header.Set("Content-Type", "application/json")

  client := &http.Client{}
  resp, err := client.Do(req)
  if err != nil {
    if iteration > 5 {
      return fmt.Errorf("Not able to access %s/greetings", baseURL)
    } else {
      log.Infof("Can't reach the service, retry one more time in 5 seconds...")
      time.Sleep(5 * time.Second)
      iteration++
      return greetingTest(baseURL, iteration)
    }
  }
  defer resp.Body.Close()

  fmt.Println("response Status:", resp.Status)
  if resp.StatusCode != 200 {
    return fmt.Errorf("Greeting service didn't answer with a 200")
  }

  fmt.Println("response Headers:", resp.Header)
  body, _ := ioutil.ReadAll(resp.Body)
  strBody := string(body)
  fmt.Println("response Body:", strBody)
  if !(strings.Contains(strBody, "Hello flag off, smoketest") || strings.Contains(strBody, "Hello flag on, smoketest")) {
    return fmt.Errorf("Greeting service didn't respond correctly")
  }
  return nil
}
