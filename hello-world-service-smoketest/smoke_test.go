package main

import (
	"fmt"
	"log"
	"net/http"
	"path/filepath"
	"testing"
	"time"
)

func TestSmokeTest(t *testing.T) {
	log.Printf("TestSmokeTest: starting HTTP server")

	srv := startHttpServer()

	time.Sleep(1 * time.Second)

	err := greetingTest("http://localhost:9090", 0)

	if err != nil {
		t.Errorf("TestSmokeTest: smoke tests failed with error: %v ", err)
	}

	time.Sleep(1 * time.Second)

	log.Printf("TestSmokeTest: stopping HTTP server")

	// now close the server gracefully ("shutdown")
	// timeout could be given instead of nil as a https://golang.org/pkg/context/
	if err := srv.Shutdown(nil); err != nil {
		t.Error(err) // failure/timeout shutting down the server gracefully
	}
}

func startHttpServer() *http.Server {
	srv := &http.Server{Addr: ":9090"}

	http.HandleFunc("/greetings/", func(w http.ResponseWriter, r *http.Request) {
		username := filepath.Base(r.URL.Path)
		w.Write([]byte(fmt.Sprintf("{\"greeting\":\"Hello flag off, %s!\"}", username)))
	})

	go func() {
		if err := srv.ListenAndServe(); err != nil {
			// cannot panic, because this probably is an intentional close
			log.Printf("Httpserver: ListenAndServe() error: %s", err)
		}
	}()

	// returning reference so caller can call Shutdown()
	return srv
}
