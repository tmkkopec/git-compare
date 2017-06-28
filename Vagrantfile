# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|

  $enable = <<-ENABLE
    set -x
    cd /home/vagrant/git-compare
    ./gradlew bootRun &
  ENABLE

  config.vm.provider "virtualbox" do |v|
    v.memory = 1024
  end

  config.vm.define "git_compare" do |git_compare|
    git_compare.vm.box = "git_compare"  
    git_compare.vm.network "forwarded_port", id: "ssh", guest: 22, host: 14650, auto_correct: true
    git_compare.vm.network "forwarded_port", id: "webserver-requests", guest: 8080, host_ip: "127.0.0.1", host: 8080, auto_correct: true

    git_compare.vm.provision "enable", type: "shell", inline: $enable
  end

end
