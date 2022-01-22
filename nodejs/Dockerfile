FROM node:alpine
WORKDIR /home/byiorio
COPY *.json .
RUN npm install
COPY *.js .
EXPOSE 8080
CMD [ "node", "client.js" ]