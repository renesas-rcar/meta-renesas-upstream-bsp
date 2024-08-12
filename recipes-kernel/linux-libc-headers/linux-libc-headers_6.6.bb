require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3)"

SRC_URI = "git://github.com/torvalds/linux.git;branch=master;protocol=https"
SRCREV = "ffc253263a1375a65fa6c9f62a893e9767fbebfa"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
