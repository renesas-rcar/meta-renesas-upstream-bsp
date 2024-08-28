SOC_FAMILY =. "rcar-gen3:"

require conf/machine/include/rcar_common.inc

IMAGE_FSTYPES += " ext4 wic.xz wic.bmap wic.xz.sha256sum"

WKS_FILE ?= "rcar-singlepart-noloader.wks"
