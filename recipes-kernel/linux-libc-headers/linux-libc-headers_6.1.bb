require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

SRC_URI = "git://github.com/renesas-rcar/upstream_bsp.git;branch=renesas-bsp/v6.1.111-2024-09-20;protocol=https"
SRCREV = "5385c18c176200e4f4dbec5114f6b4fe27f6a945"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
