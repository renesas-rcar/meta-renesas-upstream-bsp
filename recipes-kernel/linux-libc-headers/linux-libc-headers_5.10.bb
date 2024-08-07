require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3)"

RENESAS_BSP_URL = " \
    git://github.com/renesas-rcar/linux-bsp.git"
BRANCH = "v5.10.194/rcar-5.3.1"
SRC_URI = "${RENESAS_BSP_URL};branch=${BRANCH};protocol=https"
SRCREV = "6b085f452e4578dca5ba4f2d73ba1b8e0129c29a"

# Add module.lds
SRC_URI:append = " \
    file://0001-scripts-Add-module.lds-to-fix-out-of-tree-modules-bu.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
