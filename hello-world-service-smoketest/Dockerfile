FROM golang:1.12-alpine AS development

ENV CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

RUN apk add git

ENV HOME=/home/golang
WORKDIR /app
RUN adduser -h $HOME -D -u 1000 -G root golang && \
    chown golang:root /app && \
    chmod g=u /app $HOME
USER golang:root

COPY --chown=golang:root go.mod go.sum ./
RUN go mod download

COPY --chown=golang:root . ./
RUN go build -v -o hello-world-spring-microservice-smoke

ENTRYPOINT ["go"]
CMD ["test", "."]

###

FROM scratch AS production

COPY --from=development /etc/passwd /etc/group  /etc/
COPY --from=development --chown=golang:root /app/hello-world-spring-microservice-smoke /app/

USER golang:root
ENTRYPOINT ["/app/hello-world-spring-microservice-smoke"]
