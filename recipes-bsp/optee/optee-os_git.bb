DESCRIPTION = "OP-TEE OS"

LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173 \
"

inherit deploy python3native

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "python3-pycryptodome-native python3-pyelftools-native python3-cryptography-native"

PV:rcar-gen3 = "3.22.0+renesas+git${SRCPV}"
PV:rcar-gen4 = "3.13+renesas+git${SRCPV}"

BRANCH:rcar-gen3 = "rcar-gen3_3.22.0"
BRANCH:rcar-gen4 = "rcar-gen4_3.13"

SRC_URI = "git://github.com/renesas-rcar/optee_os.git;branch=${BRANCH};protocol=https"

SRCREV:rcar-gen3 = "314ecf9d01073bff837cb4f6f8f3d7b10abd0258"
SRCREV:rcar-gen4 = "6f9792db6411d6c172077eb28e584ab5f46a174e"

SRC_URI:append = " \
    file://0000-Makefile-Disable-linker-warning.patch \
"

SRC_URI:append:rcar-gen3 = " \
    file://0001-mk-gcc.mk-Change-the-path-to-the-library.patch \
    file://0002-core-arch-arm-plat-rcar-main.c.patch \
"

S = "${WORKDIR}/git"

PLATFORM:rcar-gen3 = "rcar"
PLATFORM:rcar-gen4 = "rcar_gen4"

export CROSS_COMPILE64="${TARGET_PREFIX}"

# Let the Makefile handle setting up the flags as it is a standalone application
#LD[unexport] = "1"
LDFLAGS[unexport] = "1"
#export CCcore="${CC}"
#export LDcore="${LD}"
libdir[unexport] = "1"

# Avoid compile error with GCC 10.2.0
CFLAGS += "-mno-outline-atomics"

EXTRA_OEMAKE = "-e MAKEFLAGS="

# do_install() nothing
do_install[noexec] = "1"

do_compile() {
    export CRYPTOGRAPHY_OPENSSL_NO_LEGACY=1
    oe_runmake PLATFORM=${PLATFORM} CFG_ARM64_core=y
}

do_deploy() {
    # Create deploy folder
    install -d ${DEPLOYDIR}

    # Copy TEE OS to deploy folder
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee.elf ${DEPLOYDIR}/tee-${MACHINE}.elf
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee-raw.bin ${DEPLOYDIR}/tee-${MACHINE}.bin
    install -m 0644 ${S}/out/arm-plat-${PLATFORM}/core/tee.srec ${DEPLOYDIR}/tee-${MACHINE}.srec
}

addtask deploy before do_build after do_compile
