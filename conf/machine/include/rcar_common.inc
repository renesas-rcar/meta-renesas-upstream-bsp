require conf/machine/include/soc-family.inc

MACHINE_FEATURES = ""

KERNEL_IMAGETYPE = "Image"
IMAGE_FSTYPES += " tar.bz2 ext4 wic.xz wic.bmap wic.xz.sha256sum"

WKS_FILE ?= "rcar-singlepart-noloader.wks"

SERIAL_CONSOLES = "115200;ttySC0"

# Configuration for kernel
PREFERRED_PROVIDER_virtual/kernel = "linux-renesas"

# Add default config to build eMMC boot mode (it should enable USE_EMMC_BOOTMODE = "1" local.conf)
USE_EMMC_BOOTMODE ?= "0"

# Configuration for ARM Trusted Firmware
EXTRA_IMAGEDEPENDS += " arm-trusted-firmware optee-os"

# u-boot
PREFERRED_VERSION_u-boot = "v2020.10%"
EXTRA_IMAGEDEPENDS += " u-boot"

# Add variable to Build Configuration in build log
BUILDCFG_VARS:append = " SOC_FAMILY"

# rsync is required to build kernel 5.4
HOSTTOOLS += "rsync"
