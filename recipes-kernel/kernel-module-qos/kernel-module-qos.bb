DESCRIPTION = "QoS driver for the R-Car"

LICENSE = "GPL-2.0-only & MIT"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://MIT-COPYING;md5=192063521ce782a445a3c9f99a8ad560 \
"

require include/rcar-bsp-modules-common.inc
require ${@"include/rcar-gen3-path-common.inc" if "rcar-gen3" in d.getVar("OVERRIDES") else ""}

inherit module

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

DEPENDS = "linux-renesas"

PN = "kernel-module-qos"
PR = "r0"

QOS_DRV_URL = "git://github.com/renesas-rcar/qos_drv.git"
BRANCH = "rcar-gen3"
SRC_URI = "${QOS_DRV_URL};branch=${BRANCH};protocol=https"

SRCREV:rcar-gen3 = "90981d2aa1730589fa87b50f07d9feec09396b9b"
SRCREV:rcar-gen4 = "f4c60b4ad0e96e0de3222dc42179bcade931bd76"

S = "${WORKDIR}/git"
B = "${WORKDIR}/git/qos-module/files/qos/drv"

includedir:rcar-gen3 = "${RENESAS_DATADIR}/include"

# Build Qos kernel module without suffix
KERNEL_MODULE_PACKAGE_SUFFIX = ""

do_install () {
    # Create destination directories
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -d ${D}/${includedir}

    # Install shared library to KERNELSRC(STAGING_KERNEL_DIR) for reference from other modules
    # This file installed in SDK by kernel-devsrc pkg.
    install -m 644 ${B}/Module.symvers ${KERNELSRC}/include/qos.symvers

    # Install kernel module
    install -m 644 ${B}/qos.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/

    # Install shared header files
    install -m 644 ${B}/qos.h ${KERNELSRC}/include/
    install -m 644 ${B}/qos_public_common.h ${KERNELSRC}/include/
    install -m 644 ${B}/qos_public_common.h ${D}/${includedir}/
}

PACKAGES = " \
    ${PN} \
    ${PN}-dev \
    ${PN}-dbg \
"

FILES:${PN} = " \
    ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/qos.ko \
"

FILES:${PN}-dev = " \
    ${includedir}/qos_public_common.h \
"
