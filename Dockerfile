FROM gradle:7.6-jdk17

WORKDIR /app

COPY ./ .

RUN gradle assembleFrontend
RUN gradle installDist

CMD ./build/install/app/bin/app