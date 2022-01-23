cd spa
npm run build
cd ..
rm -r src/main/resources/static/spa
mkdir src/main/resources/static/spa
cp -r spa/build/* src/main/resources/static/spa