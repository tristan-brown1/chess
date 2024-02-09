# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSgM536HHCkARYGGABBECE5cAJsOAAjYBxQxp8zNwZ9ScABb8A5igCyKAIybeTePrRHTAJkuN+zPYZMoAzM+1vb9igALL7W7naeAKyhrjYepgBsMToBngDsyf7xKAAcmXERpgCc+eGBZgAMpammZha4PC4p2WZODVphNeY+7VaxZZ5mIb1NWYXm0SN+BeVJU50tGfP9XWZ5y83jZiUbY4EOVbszng719H2b+23no8eOPTfTA47DjwvjDtEGUBAArtgwADEaGAVAAnjAAEooAxIDhgKDCJAQNBAgDuuiQYBQmEQqFIAFoAHwsaiUABcMAA2gAFADyZAAKgBdGAAel+yigAB00ABvTmUEEAWxQABoYLgOBw0dBpBKUMLgEgEABfTAUMmwYmktiSDiUqAwuHYqAACX40gQlAAFIKoCLxZKlDK5QqlSqAJQa1jsDgwHVCETifUGmBGMAAVS5dq5ju9QbEEikAZJakpZAAogAZTNwRkwe2OmAAMx+wsLXMwiZDKYJOs1es4lLQvwQCB91D9qZgNeT0spICNwhQ0dtReAoolUtdUGkCbktelPfTMAAkgA5LOQgsTqfO6Wyufu5UIdcbxl0yu0RvdwOL-thocoEeiX5gXSxoWTlAL4OPld5EpTdt13OMfwlYB310RkIAAa3Qc9LxgKCP2rB9QwDBtfX1SlUJg+D0E7Jt-XrElGwpFDoNghC0GIygsJJPF0DASkDgqXk+Xwmj0HVdBpA0b4-gBQFoA8GBswgWFUUBDEsRxZjCXI1hKNpBkWXZZRpWRNBOL3J0ZyPaR1Qo7VlK7XCYAQKSkDQC00CtcdwP3Qy5W9W99R7PtQ0pCMxygL8HR-P8k0wnV0yzXN82vILRVLcsYvQ-8wuwizmxixNiLvElvKkQdh2xN8P0C+MktCus0yAkDMx3GLHUg6jCNRTdkPwsql1I1KSLwxraKyzyyN1SjuKa+jtR1RTWJgdjOJG2i+IcjQlswIT-iBb4UEQySDD+ZhZMxbFcWQFie1MykqQEHNM0ZTNWTZLSOB03k5qI0zTpw9LrJ2997McgKXrQdyPtInKMLy8MUDAIrPwBkKOsA8lIrzAsAfiiAKza3Ll0Gjz0vwzLcZB3swYHVQUGtbEbVh9qAPCoDLtzG66ogqiPx41EADFITpYxWd0GmUvM7r+s4RihqgSkxrFya2IqKp+OWwSfjWwEjWkCSTRgABxH9-X2+SjvxZgutUrXrrugwf2e3rXpUszdT9SlkHhHXRV+61-ptwGRaJrGwwjaGqa9uHacqxGrui1Gy3RvmBbrLrHb5gnga8kmw2dsBXc4G0Q7CsOkeiy3RUTKlWS5nnwx-OPsYTyyi5QEvmR9sWzsr4u5FLqXBpl6a5c4+vG4WgTFZW5WROwX4oGwa14AKlQs-RA6FOOpTxfO+kmQtq2QS9-ufw3H8TLt960qfOes-d20AYleuD9FIHT9T5LwYD6Cg7Zprc4q1QgILlGvbRhjaC1dOpC0TvjOQzd7zP1Js+EcWcbS32CiAhG1VaoDzkEhK89cUE42Br5H8a4BBQJNhLNuKAiFdwmivKaM1+T1yIUPEeq0RJgnJtZNEMAABSEBbLax-ECeQCBQBwUNidUh69IwaTZPXa2H9aKcUmnACA1koA30IQII+WoT7dRgAAK14WgC+loPbvwIrRCUSiVHQHUaKIhD8SJP3KqTV+xVqZ+wRn-Pm7NAGxw8Xg0+PUPyJg5M5HEhMnEdV8pDBBDCBBf2xvnCOBYMEiF8Tg-xtd0qpOAFA0GMCwz-FkNiWJP5ExiisaosU+kElE3TJGGkAhRBMxyeechuCslhhIULCkVCmI0NllUDQCtFYsPWoiCEiAXywGANgaehAkQokXgbSaOjVIM2urddkagNBvU6YOa0eBRBzMvgFBx2ViYFIOdMnOuCw4bKZsAdsvjMZpwRg8zMKFnnRwrPaO5P9yQfK+WeH57TMlgMst0tefT4ADN7kMhWQA
