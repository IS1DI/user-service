param ($Ver = $("latest"))


function build
{
    ($Tag = ("is1di/userservice:$Ver"))
    ./gradlew clean build
    docker build -t $Tag .
    docker push $Tag
}
build