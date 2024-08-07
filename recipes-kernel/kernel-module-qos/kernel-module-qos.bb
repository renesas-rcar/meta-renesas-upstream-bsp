DESCRIPTION = "QoS driver for the R-Car Gen3"

LICENSE = "GPL-2.0-only & MIT"
LIC_FILES_CHKSUM = " \
    file://GPL-COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://MIT-COPYING;md5=192063521ce782a445a3c9f99a8ad560 \
"

require include/rcar-gen3-modules-common.inc

inherit module

DEPENDS = "linux-renesas"

PN = "kernel-module-qos"
PR = "r0"

QOS_DRV_URL = "git://github.com/renesas-rcar/qos_drv.git"
BRANCH = "rcar-gen3"
SRC_URI = "${QOS_DRV_URL};branch=${BRANCH};protocol=https"
SRCREV = "90981d2aa1730589fa87b50f07d9feec09396b9b"

S = "${WORKDIR}/git"
B = "${WORKDIR}/git/qos-module/files/qos/drv"

includedir = "${RENESAS_DATADIR}/include"

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
