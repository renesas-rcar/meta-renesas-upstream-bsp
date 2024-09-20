require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

SRC_URI = "git://github.com/renesas-rcar/upstream_bsp.git;branch=renesas-bsp/v6.6.52-2024-09-20;protocol=https"
SRCREV = "7d8660c647ee948f207363114d67b27ba7fe76f5"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
