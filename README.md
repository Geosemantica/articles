<a id="readme-top"></a>

<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#build">Build</a></li>
      </ul>
    </li>
    <li>
      <a href="#structure-and-contracts">Structure and contracts</a>
      <ul>
        <li><a href="#erd">ERD</a></li>
        <li><a href="#swagger">Swagger</a></li>
        <li><a href="#rabbitmq">RabbitMQ messages</a></li>
      </ul>
    </li>
    <li>
      <a href="#usage">Getting Started</a>
      <ul>
        <li><a href="#auth">Authorization and roles</a></li>
      </ul>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

The goal of this project is to demonstrate code examples and approaches to software development.
The project is a simple article service that allows users to view,
create, and edit articles and comments through REST requests.
It also demonstrates working with a message broker (RabbitMQ), such as sending messages about new articles and comments,
as well as receiving messages about user deletions.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Build

1. Build: `mvn clean package`
2. Then, run `docker compose up` and Compose will start and run entire app.

If additional configuration is required, it can be done using environment variables:

* `SERVER_ADDRESS` - App service address

  _example_: 127.0.0.1

  _default_: 0.0.0.0

* `SERVER_PORT` - App service port

  _example_: 1234

  _default_: 8090

* `DB_URL` - JDBC connection url

  _example_: jdbc:postgresql://{host}:{port}/{db_name}

* `RABBITMQ_HOST` - rabbitmq host address

* `RABBITMQ_PORT` - rabbitmq port

* `RABBITMQ_USERNAME` - rabbitmq user

* `RABBITMQ_PASSWORD` - rabbitmq password

* `RABBITMQ_VHOST` - rabbitmq virtual host

  _default:_ main

* `ACCESS_TOKEN_HEADER` - Authorization token header for REST requests

  _example_: Authorization

  _default_: X-Access-Token

* `INCLUDE_TRACE` - Include stack traces to 5XX responses

  _example_: false

  _default_: true

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Structure and contracts

### ERD

![erd](/images/erd.png)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Swagger

swagger-ui: http://127.0.0.1:8090/

swagger-ui looks like this:
![swagger](/images/swagger.png)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### RabbitMQ messages
* The service produces messages in the following format:
```json
{
  "messageId": "e730b04d-dbd6-403e-9021-de3dea75a993",
  "articleId": 3,
  "title": "New Article",
  "createdAt": "2024-07-09T20:00:00Z"
}
```
_queue: articles_

```json
{
  "messageId": "e730b04d-dbd6-403e-9021-de3dea75a993",
  "authorId": "e730b04d-dbd6-403e-9021-de3dea75a993",
  "text": "New comment",
  "createdAt": "2024-07-09T20:00:00Z"
}
```
_queue: comments_

* The service consumes messages in the following format:
```json
{
  "userId": "e730b04d-dbd6-403e-9021-de3dea75a993"
}
```
_queue: deactivated-users_
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Authorization and roles
There are 3 functional roles:
* ROLE_ADMIN - crate and edit articles
* ROLE_USER - crate and edit comments
* anonymous - can only view content
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://me.linkedin.com/company/geosemantica


## License

This code is private and proprietary. It cannot be copied, modified, distributed, or used under any circumstances without explicit permission from the owner. Unauthorized use of this code is strictly prohibited.

