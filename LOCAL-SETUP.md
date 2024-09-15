# Setting Up

This file describes the one-time setup you will need to do before running this code. These instructions are not necessary if you are using Docker or a lab machine.
Most of this content is also described in the course [zyBook](https://learn.zybooks.com/zybook/COLOSTATECS314Fall2020/chapter/1/section/8).

## Package Managers

To setup your development environment, you will need to install several dependencies on your machine. Please refer to 
the section that applies to you to find a good package manager.

### Linux

In Unix there are several package managers to install software (e.g. apt, snap). For this class, we recommend using the package 
manager which shipped with your Linux installation. For example, Ubuntu ships with apt and Fedora ships with dnf. 
There are several ways to use these to install packages, but by default we will use

```bash
sudo apt-get install [package-name]
```

If you have a different package manager, simply replace apt-get with your package manager. Almost all package managers use the same interface for installing packages.

### Mac Package Manager

For those of you working on Macs, you are able to use the Homebrew package manager, allowing you to install programs 
from the command line, just like Linux. To install Homebrew:

```bash
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

If the installation failed, ensure you meet the installation requirements
[here](https://docs.brew.sh/Installation.html). The page also has alternative installation instructions if the above 
doesn't work. Once Homebrew is installed, install packages using the following paradigm:

```bash
brew update
brew install <package-name>
```

## Dependencies

In this class, we will use Maven, a build tool for Java, and npm, a dependency manager for JavaScript development.
The following section will describe the installation process for these tools. **The lab machines already have these tools installed.**

### Java

Whether working with Linux or Mac, we recommend directly downloading a JDK and setting your `JAVA_HOME` environment 
variable rather than installing a JDK with a package manager. Use at least version 11. A JDK may be downloaded from
https://jdk.java.net/ or from [Oracle's Site](https://www.oracle.com/technetwork/java/javase/downloads/index.html).

Once you've downloaded and unpacked your JDK (to a directory like `jdk-11/`), you'll want to add the following lines to 
your `.bashrc` (`.bash_profile` for Mac users):

```bash
export JAVA_HOME=/path/to/jdk
export PATH=$JAVA_HOME/bin:$PATH
```

Restart your terminal, and try to run `java -version`. If correctly installed, you should see the proper version coming 
from the JDK you chose.

### Maven

Maven should be available for install through your package manager. On Linux, use 

```bash
sudo apt-get install maven
```

and on Mac use

```bash
brew install maven
```

Binary distributions are available form Maven's [homepage](https://maven.apache.org/)
as well. If you use the binary distribution, make sure to append the `bin/`
directory to your `PATH`, as shown above for Java.

Either way, **setting the `JAVA_HOME` variable as shown above is extremely
important,** as it tells Maven where to look for Java libraries needed to build
your project. 

Restart your terminal, and try to run `mvn --version`. If correctly installed, you should see a report from Maven, 
otherwise, you will either receive and error, or nothing.

### NPM

#### Installing with [NVM](https://github.com/creationix/nvm)

Node Version Manager is a great project that helps you install and keep track of different NodeJS versions. Its use is 
recommended, as it supports both Mac and Linux. Follow the instructions on the repository's 
[front page](https://github.com/creationix/nvm). The command below is copied from the [NVM repository's README](https://github.com/creationix/nvm).

```bash
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.3/install.sh | bash
```

Once NVM has been installed (check with `nvm --version`), you can install the latest LTS release of NodeJS with the 
following:

```bash
nvm install --lts

# See What Got Installed
nvm list

# Check To See If You Have NodeJS and NPM
node -v
npm -v
```

## Visual Studio Code

[Visual Studio Code](https://code.visualstudio.com/) is the IDE our company will be using. It has better support for the tools we will be using than Eclipse.

### vsCode installation

Visual Studio Code is free for private or commercial use. All students are able to install vsCode on their preferred platform. 
Instructions for installation can be found on the Visual Studio Code website for [Windows](https://code.visualstudio.com/docs/setup/windows), [Mac](https://code.visualstudio.com/docs/setup/mac), and [Linux](https://code.visualstudio.com/docs/setup/linux)

### Launching Visual Studio Code in CSB

To access Visual Studio Code on a lab machine, you will need to log in to the computers. You can access vsCode by using the search feature and searching "visual studio code." From there, follow the instructions below to set everything up.

### vsCode Extenstions
VSCode supports a wide eco-system of extensions that means that it supports nearly every language to some degree. It has support for 
front-end web development and Java projects. These extensions can live on the remote machine. You can use this editor for pretty much every CS class 
in our department, including for Jupyter notebooks, Python, C++/C, and more. There are some other nice features and customization you can add to your 
VSCode environment such as spell-checkers and formatters.

1. Download and install Visual Studio Code on your system using the instructions above
2. Startup Visual Studio Code and go to Extensions
* Extensions are three square, one extra square icon on the left bottom of the list located on the left side of the screen
3. Install Recommended vsCode Extensions:
* Extension Pack for Java by Microsoft
* npm Dependency Links by Rene Herrmann
* Auto Rename Tag by Jun Han
* ESLint by Microsoft
* VSCode React Refactor by planbcoding


### Building in vsCode

For the most part, the run script should be comprehensive enough to build your project. It is recommended that 
you use its internal Terminal tool when needing to do console/ terminal commands. 

To open a terminal, if it isn't already open in your lower half of the screen, Select Terminal->New Terminal. Several 
configuration builds have been included with the source files provided to you. Try running each of them and take note of what each one does.
