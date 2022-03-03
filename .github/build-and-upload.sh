#!/bin/bash

# MIT License
#
# Copyright (c) 2021 Israel Hiking Map
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

echo "Cloning SimRa-graphhopper"
git clone https://github.com/justdeko/graphhopper
cd graphhopper || exit
echo "Downloading Dockerfile and graphhopper.sh"
chmod +x ./graphhopper.sh
echo "Building docker image"
docker build . -t justdeko/simra-graphhopper:latest
docker login --username "$DOCKERHUB_USER" --password "$DOCKERHUB_TOKEN"
echo "Publishing docker image"
docker push justdeko/simra-graphhopper:latest

TAG=$(git for-each-ref --sort=committerdate refs/tags | tail -n 1 | cut -d "/" -f3)
if docker manifest inspect justdeko/simra-graphhopper:"$TAG" >/dev/null; then
    echo "No need to publish existing version: $TAG";
else
    mv Dockerfile ../Dockerfile
    mv graphhopper.sh ../graphhopper.sh
    git checkout tags/"$TAG"
    mv ../Dockerfile Dockerfile
    mv ../graphhopper.sh graphhopper.sh
    echo "Building docker image for tag: $TAG"
    docker build . -t justdeko/simra-graphhopper:"$TAG"
    echo "Publishing docker image for tag: $TAG"
    docker push justdeko/simra-graphhopper:"$TAG"
fi

