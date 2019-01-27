This is a stock web service application.

It contains only one resource with two supported methods:

**/stocks GET:**

Using this a client can request a connection through server side events. Then it will get real time stock prices updates
 to the desired stock (Apple for example)

**/stocks POST:**

Using this the application receives a new stock price update and dispatches it to all connected clients.

It generates openapi documentation automatically in the endpoint /openapi.json.

To execute the application, just run SpringBootApp main method after running maven compile command. To test it using curl:

Use the command below to get in real time every stock update for Apple (this command get be used multiple times 
to simulate a lot of connected clients):
_curl -N --http2 -H "Accept:text/event-stream" http://localhost/stocks/Apple_

Then use the command below to post stock update:

_curl -X POST "http://localhost/stocks" -H "Content-Type: application/json" -k -d "{\"name\":\"Apple\",\"price\":123.50}"_

Note that if name is not Apple, the clients will get not get notified, as expected.
