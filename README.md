# JSON Validator

## Handling the Responses

1. If received JSON is valid, it is formatted to human-readable form and sent back.

2. If JSON object is not valid, the following response is sent:

    ```json
     {
         "errorMessage" : "plain language description of the problem",
         "errorPlace" : "the point where error has occurred",
	 "resource" : "filename"
     }
     ```

## Deployment

run Docker image:

```shell
$ sudo docker run --rm -d -p 80:80 lorismelik/json_validator
```

To send JSON object, make a POST request as shown below:

```shell
$ curl --form "file=@filename" localhost:80
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
