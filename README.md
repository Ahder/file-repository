# file-repository

## Requirement 
IDE IntelliJ preferred or your IDE of your choice
you don't need to have a MySQL installed locally because we deployed the database on a web server. <br />
if you have any problem with access to the database you can do it locally <br />
by changing 3 lines in application.yaml :<br />
    url: jdbc:mysql://localhost:3336/file_manager?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC <br />
    username: yourUsermane <br />
    password: yourPassword <br />
    
The documentation of our API is available locally via Swagger on this page: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
## Auth with okta:
to get a token to test our application <br/>
Go to this uri in your nav:
```
https://{your-octa-base-uri}.okta.com/oauth2/default/v1/authorize?client_id={your-spa-client-id}&response_type=token&scope=openid&redirect_uri=http://localhost:8080/implicit/callback&state=null&nonce=foo

```
The token will be in the redirect uri <br/>
```
http://localhost:8080/implicit/callback#access_token={the-token-to-copy}&token_type=Bearer&expires_in=3600&scope=openid&state=null
```
## Test Our Application:
import our file with File_manager.postman_collection.json and copy our token to test it with Postman <br/>
## This section if you have any problems with ftp server: 
if you cannnot upload a file or download or delete it you can use a docker for launching ftp server <br /> 
The commands are:
```
docker pull stilliard/pure-ftpd:hardened

docker run -d --name ftpd_server -p 21:21 -p 30000-30009:30000-30009 -e "PUBLICHOST=localhost" -e "ADDED_FLAGS=-d -d" stilliard/pure-ftpd:hardened

docker exec -it ftpd_server sh -c "export TERM=xterm && bash"

pure-pw useradd hichem -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/bob
# this will also ask you to enter the password for your user

exit
```
you can test it with fizella 
