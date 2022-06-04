target_path=$(pwd)
echo $target_path
docker build . --build-arg version=2.5.13 --build-arg target_path=$(pwd) -t demo