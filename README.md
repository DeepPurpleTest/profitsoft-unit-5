# Simple application for sending messages to mail

## To work, you need to create a google app password, for this you need to follow these steps:
1. Go to google account settings
2. Search for 'app passwords'
3. Create a password for the application
4. In the .env file add values to the keys

   MAIL_USERNAME= email </br>
   MAIL_PASSWORD= generated application password (clear all spaces in generated password)

## To run the application, execute the command
```text
docker-compose up -d
```

The application sends messages to your email when you create a task in the producer service, to do this you need to go to swagger

### swagger link: http://localhost:8080/swagger-ui/index.html#

After that you need to create a new user with an email address and a list of projects he/she participates in.

#### POST /api/members
```json
    {
      "name": "name",
      "email": "email@gmail.com",
      "projectIds": [
          1
      ]
    } 
   ```

Then when creating a task specify it as reporter or assignee (can have both, then two messages will come up)

#### POST /api/tasks
```json
   {
      "name": "task name",
      "description": "task description",
      "project_id": 1,
      "assignee_id": 1,
      "reporter_id": 1
   }
   ```

After that within 5 minutes if the email is valid and smtp server is available, messages will be sent to the email. </br>
You can see the status of the messages in the kibana http://localhost:5601 </br>
And status of event in kafka-ui http://localhost:8082