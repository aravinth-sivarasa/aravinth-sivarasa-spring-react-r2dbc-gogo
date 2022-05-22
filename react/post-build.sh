rm -rf ../src/main/resources/templates/index.html
rm -rf ../src/main/resources/static/*
mv -vf build/index.html ../src/main/resources/templates 
mv -vf build/* ../src/main/resources/static