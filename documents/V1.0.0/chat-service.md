
# Chat Service APIs Documentation

>**version:** 1.0.0
>
>**written by:** Pouria Ghafarbeigi
> 
> **date:** 8/20/2024

Before reading this **Hamsaye** document, I recommend visiting the link`https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#websocket-stomp-overview`
to review the necessary prerequisites and gain sufficient knowledge 
about **SockJs** and **WebSocket** If you have any questions about the 
document, please email **s.beigi.79@gmail.com**.

---

### Rest HTTP APIs

**1.Loading user by user's id**
```http request
[GET] http://localhost:8057/user/id/{userId}
```
* **Parameters:**
  * `userId`: user's identify key (UUID)
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        "uid": "b45e91f0-3125-4fc6-8679-26406203200c",
        "firstname": "pouria",
        "lastname": "ghafarbeigi",
        "profilePictureId": null // The profile picture reference (uuid),
        "connectionState": {
            "socketId": "2", // The socketId is equivalent to the sessionId.
            "initiatedAt": "2024-07-23T18:00:05.349548",
            "connectionStatus" "CONNECTED"
        }
    }
    ```

  * `422`: unprocessable entity
    ```json lines
    "code": 14002,
    "message": "user not found",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```

**2.Loading messages by chat's id (conversation identify key)**
```http request
[GET] http://localhost:8057/{conversationId}/messages
```
* **Parameters:**
  * `conversationId`: conversation's identify key (UUID)
  * `pageable`: [paging and sorting doc](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        "_links" : {
            "self" : {
                "href" : "http://localhost:8080/persons{&sort,page,size}",
                "templated" : true
            },
            "next" : {
                "href" : "http://localhost:8080/persons?page=1&size=5{&sort}",
                "templated" : true
            }
        },
        "_embedded" : {
            [
                {
                    "id": "125e91f044323==",
                    "senderId": "125e91f0-3125-4fc6-8679-26406203200c",
                    "conversationId": "b44391f0-3125-4fc6-8679-26406203200c",
                    "content": "This is test message from HTML",
                    "sendAt": "2024-07-23T18:00:05.387474"
                }, 
                {
                    "id": "ea4e91f044323==",
                    "senderId": "125e91f0-3125-4fc6-8679-26406203200c",
                    "conversationId": "b44391f0-3125-4fc6-8679-26406203200c",
                    "content": "This is test message",
                    "sendAt": "2024-01-23T18:00:05.387474"
                }
            ]
        },
        "page" : {
            "size" : 5,
            "totalElements" : 50,
            "totalPages" : 10,
            "number" : 0
        }
    }
    ```
    
**3.Loading conversations belongs to specific user**
```http request
[GET] http://localhost:8057/{userId}/conversations
```
* **Parameters:**
  * `userId`: user's identify key (UUID)
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        [
            {
                "uid": "b44391f0-3125-4fc6-8679-26406203200c",
                "initiatedBy": "USER_INITIATED",
                "users": {
                    "starter": {
                        "uid": "125e91f0-3125-4fc6-8679-26406203200c",
                        "name": "pouria ghafarbeigi"
                    },
                    "continuator": {
                        "uid": "a55e91f0-3125-4fc6-8679-26416203200x",
                        "name": "mohammad hossein alikhani"
                    }
                },
                "conversationStats": {
                    "eventTiming": {
                        "lastMessageTime": "2024-07-23T18:00:05.387474"
                    },
                    "stats": {
                        "lastMessageDto": {
                            "id": "125e91f044323==",
                            "content": "This is test message"
                        }
                    } 
                }
            }
        ]
    }
    ```

**4.Delete specific conversation by its identifier**
```http request
[DELETE] http://localhost:8057/conversation/{conversationId}
```
* **Parameters:**
  * `conversationId`: conversation's identify key (UUID)
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        {
            "uid": "b44391f0-3125-4fc6-8679-26406203200c",
            "initiatedBy": "USER_INITIATED",
            "users": {
                "starter": {
                    "uid": "125e91f0-3125-4fc6-8679-26406203200c",
                    "name": "pouria ghafarbeigi"
                },
                "continuator": {
                    "uid": "a55e91f0-3125-4fc6-8679-26416203200x",
                    "name": "mohammad hossein alikhani"
                }
            },
            "conversationStats": {
                "eventTiming": {
                    "lastMessageTime": "2024-07-23T18:00:05.387474"
                },
                "stats": {
                    "lastMessageDto": {
                        "id": "125e91f044323==",
                        "content": "This is test message"
                    }
                } 
            }
        }
    }
    ```

### Web Socket APIs

**1.Start the chat**

When no conversation has taken place and the first message is sent,
the first message is created along with an instance of the 
conversation.
```http request
[SEND] http://localhost:8057/app/conversation.addConversation
```
* **Request body:**

    ```json lines
    "senderId": "a55e91f0-3125-4fc6-8679-26416203200x",
    "recipientId": "125e91f0-3125-4fc6-8679-26406203200c",
    "content": "This is the message of user"
    ```

**2.Sending chat message**

When conversation has taken place and the user want to continue
the conversation with new message.
```http request
[SEND] http://localhost:8057/app/message
```
* **Request body:**

    ```json lines
    "senderId": "a55e91f0-3125-4fc6-8679-26416203200x",
    "conversationId": "125e91f0-3125-4fc6-8679-26406203200c",
    "content": "This is the message of user"
    ```

**3.Disconnect the user**

When user sign out or close the browser this API helps to update the
user connection stats.
```http request
[SEND] http://localhost:8057/app/user.disconnectUser
```
* **Request body:**

    ```json lines
    "uid": "a55e91f0-3125-4fc6-8679-26416203200x"
    ```

**4.Subscribe public users**

User's changes update parallelism via web socket protocol.
```http request
[SUBSCRIBE] http://localhost:8057/topic/users
```
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        "uid": "b45e91f0-3125-4fc6-8679-26406203200c",
        "firstname": "pouria",
        "lastname": "ghafarbeigi",
        "profilePictureId": null // The profile picture reference (uuid),
        "connectionState": {
            "socketId": "2", // The socketId is equivalent to the sessionId.
            "initiatedAt": "2024-07-23T18:00:05.349548",
            "connectionStatus" "DISCONNECTED"
        }
    }
    ```

  * `422`: unprocessable entity
    ```json lines
    "code": 14002,
    "message": "user not found",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```

* `420`: bad request
    ```json lines
    "code": 16050,
    "message": "user information cannot be registered",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```

**5.Subscribe conversation**

Conversation's messages is updated parallelism via web socket protocol.
```http request
[SUBSCRIBE] http://localhost:8057/conversation/{conversationId}/queue/messages
```
* **Responses:**
  * `200`: successful

    ```json lines
    "code": 0,
    "message": "successful",
    "timestamp": "2024-07-23T18:00:05.387474",
    "result": {
        "id": "125e91f044323==",
        "senderId": "125e91f0-3125-4fc6-8679-26406203200c",
        "conversationId": "b44391f0-3125-4fc6-8679-26406203200c",
        "content": "This is test message from HTML",
        "sendAt": "2024-07-23T18:00:05.387474"
    }
    ```

  * `422`: unprocessable entity
    ```json lines
    "code": 14050,
    "message": "chat message not found",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```

  * `422`: unprocessable entity
    ```json lines
    "code": 14051,
    "message": "chat conversation not found",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```

  * `422`: unprocessable entity
    ```json lines
    "code": 14052,
    "message": "chat conversation already exists",
    "timestamp": "2024-07-23T18:00:05.387474",
    "cause": null // The string message shows the reason of failure
    ```
